#!/bin/bash

# Running basic Terrascan scan
terrascan scan -t aws -d infrastructure/ -o json > output/terrascan_report.json

# Running basic Tfsec scan
tfsec infrastructure/ --format json > output/tfsec_report.json
