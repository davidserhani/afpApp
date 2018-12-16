import { ITraining } from 'app/shared/model//training.model';

export interface ITeacher {
    id?: number;
    lastName?: string;
    firstName?: string;
    trainings?: ITraining[];
}

export class Teacher implements ITeacher {
    constructor(public id?: number, public lastName?: string, public firstName?: string, public trainings?: ITraining[]) {}
}
