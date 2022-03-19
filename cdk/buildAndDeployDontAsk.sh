#!/bin/sh

set -e

rm -rf ./cdk.out
mvn verify && cdk deploy --all --require-approval=never