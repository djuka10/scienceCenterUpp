import { Injectable } from "@angular/core";

@Injectable()
export class Globals {
    globalTaskId: string;
    firstAttemp: boolean = true;
    globalProcessInstance: string;
}