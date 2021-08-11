echo -e "\e[42m $(pwd)"
echo -e "\e[102m Committing the changes"
git add .
git commit -m "`date`"
git push
git status