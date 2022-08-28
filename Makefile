all:
	$(MAKE) -f tools/ManifestMakefile
	./tools/build.sh
	./tools/mysigner

clean:
	$(MAKE) -f tools/ManifestMakefile clean
	./tools/build.sh clean
