export interface ITrainee {
    id?: number;
    lastName?: string;
    firstName?: string;
}

export class Trainee implements ITrainee {
    constructor(public id?: number, public lastName?: string, public firstName?: string) {}
}
