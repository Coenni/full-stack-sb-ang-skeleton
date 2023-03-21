# web-app-full-stack: Spring Boot 2 & Angular 8
## Links for visiting webapp:
- Google Firebase: https://clients-app-ecristobale.web.app/clients
- Heroku (backend only): https://spring-boot-2clients-app.herokuapp.com/

## How tun run locally
- Local run  docker-compose up --force-recreate in main folder

admin/pass
eduardo/pass

## Scenarios
Main page without login:
![Alt text](web-app-screenshots/main_page.PNG?raw=true "Main Page")

Some warnings indicating that the requested pages are in a restricted area:
![Alt text](web-app-screenshots/area_restrictions_based_on_roles.PNG?raw=true "Main Page")

Login screen (ask me if you want me to share with you any user to see the whole application):
![Alt text](web-app-screenshots/login_page.PNG?raw=true "Login Page")

Main page with admin role:
![Alt text](web-app-screenshots/admin_main_page.PNG?raw=true "Main Page - Admin")

Main page with user role:
![Alt text](web-app-screenshots/user_main_page.PNG?raw=true "Main Page - User")

Create new client with admin role (with form validation):
![Alt text](web-app-screenshots/create_new_user_form_validation.PNG?raw=true "New Client Page - Admin")

Update client with admin role (with form validation):
![Alt text](web-app-screenshots/update_user_form.PNG?raw=true "Update Client Page - Admin")

Create new invoice with admin role (with form validation and autocomplete in product name):
![Alt text](web-app-screenshots/admin_form_new_invoice_validating_fields_autocomplete_product.PNG?raw=true "New Invoice Page - Admin")

Client details with admin role:
![Alt text](web-app-screenshots/admin_client_details.PNG?raw=true "Client Details Page - Admin")

Client details upload an image (with progress bar animation) with admin role:
![Alt text](web-app-screenshots/admin_client_photo_uploaded_with_progress_bar.PNG?raw=true "Client Details Upload An Image Page - Admin")

Client details with user role:
![Alt text](web-app-screenshots/user_client_details.PNG?raw=true "Client Details Page - User")

Invoice details with admin role:
![Alt text](web-app-screenshots/invoice_detail.PNG?raw=true "Invoice Details Page - Admin")

Invoice details with user role:
![Alt text](web-app-screenshots/user_invoice_detail.PNG?raw=true "Invoice Details Page - Admin")

Logout action:
![Alt text](web-app-screenshots/Logout_action.PNG?raw=true "Logout Page")

# UPDATE:
I liked the project idea and believe can be used as a skeleton for a new project.
But it was outdated and was open to refactoring, so I decided to do it myself
# DONE
- translating to english
- adding lombok
- upgrading angular and all other libs
- FE/BE dockerize
- BE extract auth to separate service

# TODO
- FE/BE add user management module
- FE/BE add inbox module
- FE/BE add chat module
- FE/BE add notification module
- BE replace controller pojos with dtos and implement mappings
- FE add multilang support
- FE/BE openapi/swagger Contract First Approach
- BE upgrade java/mvn
- BE/DB atomity of service tables
- FE/BE test coverage
- microfrontend approach
- github pipelines

# Bugs
- not showing images properly 
