## Return the number of question with Solution_ pattern
function count_leetcode_questions() {
    echo '========================================================'
    echo 'Total number of questions :'
    ls -R | grep 'Solution_' | wc -l
    echo '========================================================'
}

## Returns the list of duplicate questions
function get_duplicate_questions() {
    echo '========================================================'
    echo 'List of Duplicate questions :'
    ls -R | grep Solution_ | sort | uniq -c | grep -v '1 Solution_'
    echo '========================================================'
}

## run the method
$1