import { Moment } from 'moment';
import { ITrainee } from 'app/shared/model//trainee.model';
import { ITeacher } from 'app/shared/model//teacher.model';

export interface ITraining {
    id?: number;
    start?: Moment;
    end?: Moment;
    courseId?: number;
    trainees?: ITrainee[];
    teachers?: ITeacher[];
}

export class Training implements ITraining {
    constructor(
        public id?: number,
        public start?: Moment,
        public end?: Moment,
        public courseId?: number,
        public trainees?: ITrainee[],
        public teachers?: ITeacher[]
    ) {}
}
