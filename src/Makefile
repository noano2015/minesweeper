JARNAME = minesweeper

all:
	(cd core; make $(MFLAGS) all)
	(cd visual; make $(MFLAGS) all)

clean:
	(cd core; make $(MFLAGS) clean)
	(cd visual; make $(MFLAGS) clean)

install:
	(cd core; make $(MFLAGS) install)
	(cd visual; make $(MFLAGS) install)

execute:
	(cd visual; java Launcher.java)

build:
	(javac -encoding UTF-8 `find . -name \*.java`)
	(jar cf ./$(JARNAME).jar `find . -name \*.class -o -name \*.java` `find ./visual/images/ -name \*.png` `find ./visual/Fonts/digital_7 -name \*.ttf` )
