NAME = veletlenszeru
VER = 0.1.1
TARGET = target/uberjar/$(NAME)-$(VER)-standalone.jar

JAVA := $(shell which java)

.PHONY: build test

all: build test

build:
	lein uberjar
	echo "#!$(JAVA) -jar" > $(NAME)
	cat $(TARGET) >> $(NAME)
	chmod +x $(NAME)

test:
	@ echo -e "\e[95m\e[1mtesting jar\e[0m"
	java -jar $(TARGET)
	@ echo -e "\e[95m\e[1mtesting executable\e[0m"
	./$(NAME)

clean:
	lein clean
	rm $(NAME)

install:
	install -Dm755 "$(NAME)" "$(DESTDIR)/usr/bin/$(NAME)"
	install -Dm644 "LICENSE" "$(DESTDIR)/usr/share/licenses/$(NAME)/LICENSE"

uninstall:
	rm -rfv "$(DESTDIR)/usr/bin/$(NAME)" "$(DESTDIR)/usr/share/licenses/$(NAME)"
