export class SignUpInfo {
    firstName: string;
    email: string;
    role: string[];
    password: string;
    lastName: string;
    address: string;
    phoneNumber: string;

    constructor(firstName: string, email: string, password: string,
                lastName:string, address:string, phoneNumber:string) {
        this.firstName = firstName;
        this.email = email;
        this.password = password;
        this.lastName = lastName;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.role = ['ROLE_USER'];
    }
}