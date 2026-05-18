{
    description = "GitMetrics devshell";

    inputs = {
        nixpkgs.url = "github:nixos/nixpkgs?ref=nixos-unstable";
    };
    outputs = {self, nixpkgs }:
        let 
            pkgs = nixpkgs.legacyPackages."x86_64-linux";
        in
            {
            devShells."x86_64-linux".default = pkgs.mkShell {
                packages = [ 
                    pkgs.nodejs_22
                    pkgs.vitejs
                    pkgs.jdk21
                    pkgs.maven
                    pkgs.docker
                    pkgs.docker-compose
                    pkgs.postgresql
                    pkgs.nginx
                ];
                shellHook = ''
                    echo "-------------------"
                    echo "Entered environment"
                    export SHELL="${pkgs.zsh}/bin/zsh";
                '';
            };
        };
}
