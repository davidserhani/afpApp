import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { NgbDateAdapter } from '@ng-bootstrap/ng-bootstrap';

import { NgbDateMomentAdapter } from './util/datepicker-adapter';
import { AfpappSharedLibsModule, AfpappSharedCommonModule, HasAnyAuthorityDirective } from './';

@NgModule({
    imports: [AfpappSharedLibsModule, AfpappSharedCommonModule],
    declarations: [HasAnyAuthorityDirective],
    providers: [{ provide: NgbDateAdapter, useClass: NgbDateMomentAdapter }],
    exports: [AfpappSharedCommonModule, HasAnyAuthorityDirective],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AfpappSharedModule {
    static forRoot() {
        return {
            ngModule: AfpappSharedModule
        };
    }
}
