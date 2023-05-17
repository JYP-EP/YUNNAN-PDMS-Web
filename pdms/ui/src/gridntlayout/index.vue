<template>
  <div class="app-wrapper" style="height: 100%">
    <dsfh-header></dsfh-header>
    <app-main />
  </div>
</template>

<script>
import DsfhHeader from '@/layout/components/DsfhHeader';
import AppMain from '@/layout/components/AppMain';

export default {
  name: 'GridntLayout',
  components: {
    DsfhHeader,
    AppMain
  }
};
</script>

<style scoped>
.app-main {
  height: calc(100% - 90px);
  background-image: url('../assets/images/bg.png');

}
</style>
