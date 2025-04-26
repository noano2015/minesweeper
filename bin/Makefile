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
