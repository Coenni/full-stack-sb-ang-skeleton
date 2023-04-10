export class User {
    id?: string;
    username?: string;
    password?: string;
    firstname?: string;
    lastname?: string;
    token?: string;
    roles: string[] = [];
}
