package com.mael_minard.gitmetrics.converter;

import com.mael_minard.gitmetrics.dto.LatestReleaseGithubResponse;
import com.mael_minard.gitmetrics.dto.RepoGithubResponse;
import com.mael_minard.gitmetrics.dto.RepoMetricsResponse;
import jakarta.annotation.Nullable;

import java.time.Duration;
import java.time.Instant;
import java.util.Map;

public class RepoMetricsConverter {
    private static int getPushScore(Duration time_since_last_push) {
        if (time_since_last_push == null) {
            return 0;
        } else if (time_since_last_push.toDays() > 30) {
            return 5;
        } else if (time_since_last_push.toDays() > 7) {
            return 20;
        } else if (time_since_last_push.toDays() > 1) {
            return 50;
        } else if (time_since_last_push.toHours() > 5) {
            return 80;
        } else {
            return 100;
        }
    }

    private static int getOpenIssuesScore(int open_issues_count) {
        if (open_issues_count == 0) {
            return 0;
        } else if (open_issues_count == 1) {
            return 20;
        } else if (open_issues_count < 4) {
            return 50;
        } else if (open_issues_count < 11) {
            return 80;
        } else {
            return 100;
        }
    }

    private static int getReleaseScore(Duration time_since_latest_release) {
        if (time_since_latest_release == null) {
            return 0;
        } else if (time_since_latest_release.toDays() > 365) {
            return 10;
        } else if (time_since_latest_release.toDays() > 30) {
            return 20;
        } else if (time_since_latest_release.toDays() > 7) {
            return 50;
        } else if (time_since_latest_release.toDays() > 3) {
            return 80;
        } else {
            return 100;
        }
    }

    private static int calculateActivityScore(RepoGithubResponse repo,
                                              @Nullable String latest) {
        String last_push = repo.getPushed_at();
        int open_issues_count = repo.getOpen_issues_count();

        Instant now = Instant.now();
        Duration time_since_last_push = last_push == null ? null :
                Duration.between(Instant.parse(last_push), now);
        Duration time_since_latest_release = latest == null ? null :
                Duration.between(Instant.parse(latest), now);

        int push_score = getPushScore(time_since_last_push);
        int open_issues_score = getOpenIssuesScore(open_issues_count);
        int release_score = getReleaseScore(time_since_latest_release);

        int push_score_coef = 4;
        int open_issues_score_coef = 3;
        double release_score_coef = 0.4;
        double max = (push_score_coef*100+open_issues_score_coef*100+release_score_coef*100);

        double result = (push_score_coef*push_score +
                open_issues_score_coef*open_issues_score +
                release_score_coef*release_score) * 100 / max;
        return (int) Math.floor(result);
    }

    private static int calculatePopularityScore(RepoGithubResponse repo) {
        int stargazers = repo.getStargazers_count();
        int forks = repo.getForks_count();
        int watchers = repo.getSubscribers_count();

        double S =
                10 * stargazers
                        + 100 * forks
                        + 30 * Math.sqrt(watchers);

        double a = 0.0065;
        double b = 0.65;

        double result =
                100 * (
                        1 - Math.exp(
                                -a * Math.pow(S, b)
                        )
                );

        result = Math.clamp(result, 0, 100);

        return (int) Math.floor(result);

    }

    public static RepoMetricsResponse toRepoMetricsResponse(RepoGithubResponse repo,
                                                     Map<String, Integer> languages,
                                                     @Nullable LatestReleaseGithubResponse latest_release) {
        String latest_release_published_at = latest_release == null ? null : latest_release.getPublished_at();
        RepoMetricsResponse resp = new RepoMetricsResponse();
        resp.setFull_name(repo.getFull_name());
        resp.setDescription(repo.getDescription());
        resp.setHtml_url(repo.getHtml_url());
        resp.setCreated_at(repo.getCreated_at());
        resp.setPushed_at(repo.getPushed_at());
        resp.setUpdated_at(repo.getUpdated_at());
        resp.setForks_count(repo.getForks_count());
        resp.setStargazers_count(repo.getStargazers_count());
        resp.setSubscribers_count(repo.getSubscribers_count());
        resp.setOpen_issues_count(repo.getOpen_issues_count());
        resp.setOwner(repo.getOwner());
        resp.setSecurity_and_analysis(repo.getSecurity_and_analysis());
        resp.setLanguages(languages);
        resp.setLatest_release_published_at(latest_release_published_at);
        resp.setActivity_score(calculateActivityScore(repo, latest_release_published_at));
        resp.setPopularity_score(calculatePopularityScore(repo));
        return resp;
    }
}
