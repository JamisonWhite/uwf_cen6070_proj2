# Generating EMMA reports
cd ..
jenv local 1.6
java -ea -jar emma.jar -r html -cp "." -sp "../../src" TestDriver

