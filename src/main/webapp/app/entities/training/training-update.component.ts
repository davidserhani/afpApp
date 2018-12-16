import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';

import { ITraining } from 'app/shared/model/training.model';
import { TrainingService } from './training.service';

@Component({
    selector: 'jhi-training-update',
    templateUrl: './training-update.component.html'
})
export class TrainingUpdateComponent implements OnInit {
    training: ITraining;
    isSaving: boolean;
    startDp: any;
    endDp: any;

    constructor(protected trainingService: TrainingService, protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ training }) => {
            this.training = training;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.training.id !== undefined) {
            this.subscribeToSaveResponse(this.trainingService.update(this.training));
        } else {
            this.subscribeToSaveResponse(this.trainingService.create(this.training));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<ITraining>>) {
        result.subscribe((res: HttpResponse<ITraining>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }
}
