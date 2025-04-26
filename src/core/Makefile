JARNAME= mine-core

all:
	(javac -encoding UTF-8 `find . -name \*.java`)
	(jar cf ../$(JARNAME).jar `find . -name \*.class -o -name \*.java`)

clean:
	$(RM) $(JARNAME).jar `find . -name \*.class`