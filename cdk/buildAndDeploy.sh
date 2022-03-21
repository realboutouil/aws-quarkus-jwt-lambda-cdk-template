#!/bin/sh

set -e

mvn verify && cdk deploy