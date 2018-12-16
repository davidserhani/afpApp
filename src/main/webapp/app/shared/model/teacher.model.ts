export interface ITeacher {
    id?: number;
    lastName?: string;
    firstName?: string;
}

export class Teacher implements ITeacher {
    constructor(public id?: number, public lastName?: string, public firstName?: string) {}
}
