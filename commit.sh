echo -e "$(pwd) "
echo -e "\e[102m Committing the changes \e[49m"
echo -e "\e[92m"
git status
git add .
echo -e "\e[94m"
git commit -m "`date`"
echo -e "\e[93m"
git push
echo -e "\e[92m"
git status