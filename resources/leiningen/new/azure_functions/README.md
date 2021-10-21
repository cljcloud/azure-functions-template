# {{name}}

Serverless ClojureScript app on Azure Functions

## Running locally

Install Azure Functions Core Tools

    $ npm i -g azure-functions-core-tools@3 --unsafe-perm true

Create local profiles.clj to store secrets

    $ cp example.profiles.clj profiles.clj

Start app in dev mode:

    $ lein watch

nREPL automatically started on 7002 port

Start Azure Func (starts node process, required for REPL):

    $ lein azure

Setup remote nREPL configuration, specify `port: 7002`.
Run the REPL, connect to app by running directly in REPL input window.

`(shadow/repl :azure)`

## Running tests

Run in dev mode with watch

      $ lein shadow watch test

or run once, for CI:

      $ lein test

## Release Azure Functions

Run release task, it's pre-configured to use `prod` profile.

    $ lein release:prod

Publish to Azure Function

With uploading the local settings file, 
if released with production profile, should contains production settings.

    $ cd target/azure
    $ func azure functionapp publish <FunctionAppName> --publish-local-settings

Additionally:

To fetch remote settings into local settings file (overwrites)

    $ func azure functionapp fetch-app-settings <FunctionAppName>

Just upload the local settings file (without code change)

    $ func azure functionapp publish <FunctionAppName> --publish-settings-only

## License

OpenSource © 2021
