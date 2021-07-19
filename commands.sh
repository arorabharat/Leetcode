## Assign the input argument to variable, so that method could use it
var2=$2
var3=$3
var4=$4
var5=$5
var6=$6

line="========================================================"

## Return the number of question with Solution_ pattern
function count_leetcode_questions() {
    echo $line
    echo "Total number of questions :"
    ls -R | grep "Solution_" | wc -l
    echo $line
}

## Returns the list of duplicate questions
function get_duplicate_questions() {
    echo $line
    echo "List of Duplicate questions :"
    ls -R | grep Solution_ | sort | uniq -c | grep -v "1 Solution_"
    echo $line
}

# Return the solution if exits
function find_questions() {
    echo $line
    echo "Searching for files Solution_$var2 :"
    ls -R | grep "Solution_$var2"
    echo $line
}

## run the method
$1