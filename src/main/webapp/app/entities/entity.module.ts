import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { AfpappCourseModule } from './course/course.module';
import { AfpappTeacherModule } from './teacher/teacher.module';
import { AfpappTraineeModule } from './trainee/trainee.module';
import { AfpappTrainingModule } from './training/training.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    // prettier-ignore
    imports: [
        AfpappCourseModule,
        AfpappTeacherModule,
        AfpappTraineeModule,
        AfpappTrainingModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AfpappEntityModule {}
