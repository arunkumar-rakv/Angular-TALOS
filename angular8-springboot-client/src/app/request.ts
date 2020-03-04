export class Request {
    id: number;
    appId: string;
    region: string;
    environment: string;
    appName: string;
    asgDesired: number;
    asgMax: number;
    asgMin: number;
}