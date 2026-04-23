# Image Service API

A small Spring Boot API that exposes image-related endpoints and returns image metadata responses.

## Overview

This application provides two HTTP endpoints:

- `POST /images` — upload an image file
- `GET /images?label=...` — retrieve image metadata by label

The API entry point is implemented in:

- `src/main/java/com/set/image_service_api/controller/ImageController.java`

Current services:

- `src/main/java/com/set/image_service_api/service/ImageUploadService.java`
- `src/main/java/com/set/image_service_api/service/ImageQueryService.java`

## Technology Stack

- Java 21
- Spring Boot 4
- Spring Web MVC
- Springdoc OpenAPI
- Maven

## Project Structure

```text
src/
  main/
    java/com/set/image_service_api/
      controller/
      service/
      dto/
      config/
    resources/
      application.properties
  test/
    java/com/set/image_service_api/
