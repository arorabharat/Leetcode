echo -e "$(pwd) "
echo -e "\e[102m Committing the changes \e[49m"
echo -e ""
git add .
git commit -m "`date`"
git push
git status