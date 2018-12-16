import { ITraining } from 'app/shared/model//training.model';

export const enum Title {
    CDI = 'CDI',
    DL = 'DL',
    SYSADMIN = 'SYSADMIN'
}

export interface ICourse {
    id?: number;
    description?: string;
    title?: Title;
    trainings?: ITraining[];
}

export class Course implements ICourse {
    constructor(public id?: number, public description?: string, public title?: Title, public trainings?: ITraining[]) {}
}
