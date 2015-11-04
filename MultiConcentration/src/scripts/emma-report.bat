
@echo Generating EMMA reports
cd ..
jenv local 1.6
java -jar emma.jar -r html -cp "." -sp "." TestDriver