#!/bin/bash

mkdir -p reports

# Running terrascan scan
/usr/local/bin/terrascan scan -t aws -d infrastructure/ -o json > reports/terrascan_report.json

#Running TFsec scan
/usr/local/bin/tfsec infrastructure/ --format json > reports/tfsec_report.json

echo "Terrascan and tfsec reports saved to $(pwd)/reports/"

