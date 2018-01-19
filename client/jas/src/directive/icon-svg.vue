 <template>


      <svg class="icon" aria-hidden="true">
        <use :xlink:href="icon_name"></use>
      </svg>

</template>

<script>
    export default {
        props: ['icon_name'],
        /*methods: {
            closeTip(){
                this.$emit('closeTip')
            }
        }*/
    }
</script>

