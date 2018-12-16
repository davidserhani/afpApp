export const enum Title {
    CDI = 'CDI',
    DL = 'DL',
    SYSADMIN = 'SYSADMIN'
}

export interface ICourse {
    id?: number;
    description?: string;
    title?: Title;
}

export class Course implements ICourse {
    constructor(public id?: number, public description?: string, public title?: Title) {}
}
