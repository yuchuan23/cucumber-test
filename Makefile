run:
	./gradlew run
clean:
	./gradlew clean
# make cucumber tags=@player host=https://jsonplaceholder.typicode.com
cucumber:
	./gradlew cucumber -Ptags="$(tags)" -Dhost="$(host)"

# make api tags=@fakeApi
api:
	./gradlew cucumber -Ptags="$(tags)" -Dhost="https://jsonplaceholder.typicode.com"
