## Return the number of question with Solution_ pattern
function count_leetcode_questions() {
    ls -R | grep 'Solution_' | wc -l
}

function get_duplicate_questions() {
    ls -R | grep Solution_ | sort | uniq -c | grep -v '1 Solution_'
}

## run the method
$1