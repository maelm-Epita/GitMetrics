<h1 align="center"> GitMetrics </h1>
<p align="center"> <i> An intuitive Web App to view metrics for your profile or repositories 🎯 </i> </p>

<h2 align="center"> 🚧 This project is a work in progress 🚧 </h2>

## MVP TODO
- [X] Landing page
- [X] Repository search
- [ ] Repository dashboard page
- [ ] Live server deployment

## FUTURE TODO
- [ ] GitHub OAUTH2
- [ ] User dashboard page
- [ ] CD/CI Automation

# Starting the server locally
## Dependencies
- Node.js
- Vite
- JDK
- Maven

## Starting the Front End (Client)
- Navigate to `client/`
- Rename the .env.example file to .env
- Run `npm install`
- Run `npm run dev`

The client is reachable on `http://localhost:5173` by default.

## Starting the Back End (Server)
### GitHub token
As the server requests data from the GitHub API, it uses a GitHub personal access token to make more requests without reaching the rate limit.
You will need a GitHub Personal Access Token to launch the server locally

See the [Official GitHub documentation for creating a fine grained personal access token](https://docs.github.com/en/authentication/keeping-your-account-and-data-secure/managing-your-personal-access-tokens#creating-a-fine-grained-personal-access-token)

### Starting the server
- Navigate to `server/`
- Set the GITHUB_TOKEN environment variable to your personal access token
```GITHUB_TOKEN={token}``` for windows cmd
```export GITHUB_TOKEN={token}``` for bash
- run `mvn spring-boot:run

The server is reachable on `http://localhost:8080` by default
