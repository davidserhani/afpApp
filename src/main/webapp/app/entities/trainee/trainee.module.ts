import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AfpappSharedModule } from 'app/shared';
import {
    TraineeComponent,
    TraineeDetailComponent,
    TraineeUpdateComponent,
    TraineeDeletePopupComponent,
    TraineeDeleteDialogComponent,
    traineeRoute,
    traineePopupRoute
} from './';

const ENTITY_STATES = [...traineeRoute, ...traineePopupRoute];

@NgModule({
    imports: [AfpappSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        TraineeComponent,
        TraineeDetailComponent,
        TraineeUpdateComponent,
        TraineeDeleteDialogComponent,
        TraineeDeletePopupComponent
    ],
    entryComponents: [TraineeComponent, TraineeUpdateComponent, TraineeDeleteDialogComponent, TraineeDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AfpappTraineeModule {}
