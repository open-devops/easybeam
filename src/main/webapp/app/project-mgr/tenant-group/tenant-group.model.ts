import { TenantUser } from './../tenant-user'
import { TenantRole } from './../tenant-role'

export class UserRole {
    constructor(
        public user?: TenantUser,
        public role?: TenantRole
    ) {
    }
}

export class TenantGroup {
    constructor(
        public id?: number,
        public name?: string,
        public description?: string,
        public accountId?: number,
        public userRoles?: UserRole[],
    ) {
    }
}
