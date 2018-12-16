import { Moment } from 'moment';

export interface ITraining {
    id?: number;
    start?: Moment;
    end?: Moment;
}

export class Training implements ITraining {
    constructor(public id?: number, public start?: Moment, public end?: Moment) {}
}
