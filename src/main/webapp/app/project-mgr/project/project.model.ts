import { BaseEntity } from './../../shared';

export class Project implements BaseEntity {
    constructor(
        public id?: number,
        public name?: string,
        public description?: string,
        public isActive?: boolean,
        public isPublic?: boolean,
        public accountId?: number,
    ) {
        this.isActive = true;
        this.isPublic = false;
    }
}
