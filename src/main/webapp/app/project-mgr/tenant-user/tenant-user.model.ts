export class TenantUser {
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
