#!/bin/bash
# System report for Yernar Temirkhan. Optional argument: student full name.
STUDENT_NAME="${1:-Yernar Temirkhan}"
BASE_DIR="$(cd "$(dirname "$0")/.." && pwd)"
LOG_FILE="$BASE_DIR/logs/Temirkhan_Yernar_system_report.log"

{
  echo "=============================================="
  echo " SYSTEM REPORT"
  echo "=============================================="
  echo "Student: $STUDENT_NAME"
  echo "Date and time: $(date '+%Y-%m-%d %H:%M:%S')"
  echo "Current user: $(whoami)"
  echo "Operating system: $(grep PRETTY_NAME /etc/os-release | cut -d= -f2 | tr -d '"')"
  echo "Kernel version: $(uname -r)"
  echo ""
  echo "--- CPU ---"
  echo "Architecture: $(uname -m)"
  echo "CPU cores: $(nproc)"
  lscpu | grep -E 'Model name|Vendor ID'
  echo ""
  echo "--- Memory ---"
  free -h
  echo ""
  echo "--- Disk usage ---"
  df -h
  echo ""
  echo "--- Available disk space (/) ---"
  df -h / | awk 'NR==2 {print "Available: " $4 " of " $2}'
  echo ""
} | tee -a "$LOG_FILE"

