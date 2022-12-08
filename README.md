# Validação de Arquiteturas em Domain-Driven Design com Testes Arquiteturais

## Dependências
- Gradle 7.5
- Java 17

## Instalação

```shell
    ./gradlew build
```

## Testar definição das regras

```shell
    ./gradlew test
```

## Testar regras no domínio de exemplo

```shell
    ./gradlew :app:test --tests "com.kaelfeitosa.archunitddd.architecture.ArchRules"
```