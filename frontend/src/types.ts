export type AcceleratorClass = 'A100' | 'L40S'

export type CreateReservationRequest = {
    acceleratorClass: AcceleratorClass,
    quantity: number
}

export type CreateReservationResponse = {
    reservationId: string
}

export type ApiError = {
    code: string,
    message: string
}