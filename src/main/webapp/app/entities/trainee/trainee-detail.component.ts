import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITrainee } from 'app/shared/model/trainee.model';

@Component({
    selector: 'jhi-trainee-detail',
    templateUrl: './trainee-detail.component.html'
})
export class TraineeDetailComponent implements OnInit {
    trainee: ITrainee;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ trainee }) => {
            this.trainee = trainee;
        });
    }

    previousState() {
        window.history.back();
    }
}
