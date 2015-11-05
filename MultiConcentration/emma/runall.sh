# Generating EMMA reports
cd ..
java -ea -jar emma.jar -r html -cp "." -sp "../../src" TestDriver
cd scripts
