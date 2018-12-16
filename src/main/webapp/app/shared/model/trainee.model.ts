export interface ITrainee {
    id?: number;
    lastName?: string;
    firstName?: string;
    trainingId?: number;
}

export class Trainee implements ITrainee {
    constructor(public id?: number, public lastName?: string, public firstName?: string, public trainingId?: number) {}
}
