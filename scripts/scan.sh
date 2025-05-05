#!/bin/bash

# Running basic Terrascan scan
terrascan scan -t aws -d terraform/ -o json > output/terrascan_report.json

# Running basic Tfsec scan
tfsec terraform/ --format json > output/tfsec_report.json
