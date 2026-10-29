
#!/bin/bash
# API test for Temirkhan Yernar Student API (curl). Optional argument: base URL.
BASE="${1:-http://localhost:8082}"

call() {  # $1 title, $2 method, $3 path, $4 optional JSON body
  echo ""
  echo "=== $1 ==="
  echo "$2 $BASE$3"
  if [ -n "$4" ]; then
    OUT=$(curl -s -w "\n%{http_code}" -X "$2" "$BASE$3" -H "Content-Type: application/json" -d "$4")
  else
    OUT=$(curl -s -w "\n%{http_code}" -X "$2" "$BASE$3")
  fi
  BODY=$(echo "$OUT" | sed '$d')
  CODE=$(echo "$OUT" | tail -n 1)
  echo "$BODY"
  echo "HTTP status: $CODE"
}

echo "API test for Student API - student: Yernar Temirkhan - $(date '+%Y-%m-%d %H:%M:%S')"

call "1. Application info" GET "/api/info"

call "2. Create a student (201 expected)" POST "/api/students" \
  '{"firstName":"Yernar","lastName":"Temirkhan","email":"yernar.api.test@example.com","groupName":"IT2-2314"}'
ID=$(echo "$BODY" | python3 -c 'import sys,json; print(json.load(sys.stdin)["id"])')
echo "Created student id: $ID"

call "3. Get all students (200 expected)" GET "/api/students"

call "4. Get one student by id (200 expected)" GET "/api/students/$ID"

call "5. Update the student (200 expected)" PUT "/api/students/$ID" \
  '{"firstName":"Yernar Updated","lastName":"Temirkhan","email":"yernar.api.test@example.com","groupName":"IT2-2314-UPDATED"}'

call "6. Get the updated student (200 expected)" GET "/api/students/$ID"

call "7. Search by last name (200 expected)" GET "/api/students/search?lastName=temirkhan"

call "8. Delete the student (204 expected)" DELETE "/api/students/$ID"

call "9. Get the deleted student (404 expected)" GET "/api/students/$ID"

call "10. Get a nonexistent student (404 expected)" GET "/api/students/999999"

call "11. Invalid data, bad email (400 expected)" POST "/api/students" \
  '{"firstName":"Bad","lastName":"Email","email":"not-an-email","groupName":"IT2-2314"}'

echo ""
echo "API test finished."
