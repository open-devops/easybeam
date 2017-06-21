import { BaseEntity } from './../../shared';

export class TenantUser implements BaseEntity {
    constructor(
        public id?: number,
        public login?: string,
        public password?: string,
        public firstName?: string,
        public lastName?: string,
        public email?: string,
        public langKey?: string,
        public accountId?: number,
        public groups?: number[],
        public roles?: number[],
    ) {
    }
}
