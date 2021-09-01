run:
	./gradlew run
clean:
	./gradlew clean
# make cucumber tags=@player
cucumber:
	./gradlew cucumber -Ptags="$(tags)"
