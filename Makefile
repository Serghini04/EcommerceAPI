.PHONY: up down build logs restart

all : up

up:
	docker-compose up --build -d


down:
	docker-compose down


build:
	docker-compose build

logs:
	docker-compose logs -f


restart:
	docker-compose restart

clean:
	docker-compose down --volumes --rmi all --remove-orphans