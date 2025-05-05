#!/bin/bash

# Running basic Terrascan scan
terrascan scan -t aws -d infrastructure/ -o json > /home/zwi/terrascan_report.json

# Running basic Tfsec scan
tfsec infrastructure/ --format json > /home/zwi/tfsec_report.json
