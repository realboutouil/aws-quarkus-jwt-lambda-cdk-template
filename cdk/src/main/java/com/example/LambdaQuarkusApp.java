package com.example;

import software.amazon.awscdk.App;
import software.amazon.awscdk.Environment;
import software.amazon.awscdk.StackProps;
import software.amazon.awscdk.Tags;

import java.io.IOException;
import java.util.Objects;

public class LambdaQuarkusApp {

    public static void main(final String[] args) throws IOException {
        var app = new App();
        var appName = "aws-quarkus-jwt-lambda-cdk-template";

        Tags.of(app).add("project", "Secure Quarkus App on AWS Lambda");
        Tags.of(app).add("environment", "development");
        Tags.of(app).add("application", appName);

        var stackProps = createStackProperties();
        var httpAPIGatewayIntegration = true;

        new LambdaQuarkusStack(app, appName, stackProps, httpAPIGatewayIntegration);
        app.synth();
    }

    static StackProps createStackProperties() {
        var account = System.getenv("CDK_DEPLOY_ACCOUNT");
        var region = System.getenv("CDK_DEPLOY_REGION");

        if (Objects.isNull(account))
            return StackProps.builder().build();
        var environment = Environment.builder()
                .account(account)
                .region(region)
                .build();
        return StackProps.builder()
                .env(environment)
                .build();
    }
}
